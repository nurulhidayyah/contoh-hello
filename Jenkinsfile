// Pipeline hello: langkah yang sama dengan percobaan manual, dijalankan Jenkins.
//   1. uji, bangun image dengan Jib, dorong ke registry platform
//   2. catat tag baru di platform-gitops; Argo CD yang memasangnya
// Test yang gagal menghentikan pipeline di langkah 1, jadi image rusak tidak
// pernah sampai ke registry, apalagi ke cluster.
pipeline {
    agent any
    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '20'))
    }
    stages {
        stage('Uji dan bangun image') {
            steps {
                script {
                    env.TAG = sh(returnStdout: true, script: 'git rev-parse --short=7 HEAD').trim()
                }
                sh './mvnw -B verify jib:build -Dtag=$TAG'
            }
        }
        stage('Catat tag di platform-gitops') {
            steps {
                sshagent(['platform-gitops-deploy-key']) {
                    sh '''
                        rm -rf gitops
                        git clone -q git@github.com:nurulhidayyah/platform-gitops.git gitops
                        cd gitops
                        sed -i "s|contoh/hello:.*|contoh/hello:$TAG|" projects/contoh/dev/hello.yaml
                        if git diff --quiet; then
                            echo "tag $TAG sudah terpasang, tidak ada yang dicatat"
                            exit 0
                        fi
                        git -c user.name=platform-jenkins -c user.email=jenkins@platform.invalid \
                            commit -qam "deploy: hello $TAG"
                        # Commit lain bisa masuk sejak clone; tarik dulu supaya push tidak ditolak.
                        git pull -q --rebase origin main
                        git push -q origin HEAD:main
                    '''
                }
            }
        }
    }
}
