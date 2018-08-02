pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building Application: accounts...'
                sh './gradlew -p applications/accounts/'
                echo 'Building Application: orders...'
                sh './gradlew -p applications/orders/'
                echo 'Building Application: products...'
                sh './gradlew -p applications/products/'
                echo 'Building Application: shipments...'
                sh './gradlew -p applications/shipments/'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Logging in to CF...'
                sh 'cf login -a https://api.run.pivotal.io -u jyarbrough@solstice.com -p Pivotal123!! -o solstice-org -s jyarbrough-cnt'
                echo 'Deploying....'

                sh 'cf push accounts -t 180 -p applications/accounts/build/libs/accounts-0.0.1-SNAPSHOT.jar'
                sh 'cf push orders -t 180 -p applications/orders/build/libs/orders-0.0.1-SNAPSHOT.jar'
                sh 'cf push products -t 180 -p applications/products/build/libs/products-0.0.1-SNAPSHOT.jar'
                sh 'cf push shipments -t 180 -p applications/shipments/build/libs/shipments-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}