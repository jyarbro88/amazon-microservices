pipeline {
    agent any
    environment{
        password = credentials('cf-creds')
    }
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
                sh 'cf login -a https://api.run.pivotal.io -u jyarbrough@solstice.com -p $password -o solstice-org -s jyarbrough-cnt'
                echo 'Deploying....'

                sh 'cf push accounts --random-route -p /Users/joeyarbrough/Projects/challenges/amazon-micro-services/applications/accounts/build/libs/accounts-0.0.1-SNAPSHOT.jar'
                sh 'cf push orders --random-route -p /Users/joeyarbrough/Projects/challenges/amazon-micro-services/applications/orders/build/libs/orders-0.0.1-SNAPSHOT.jar'
                sh 'cf push products --random-route -p /Users/joeyarbrough/Projects/challenges/amazon-micro-services/applications/products/build/libs/products-0.0.1-SNAPSHOT.jar'
                sh 'cf push shipments --random-route -p /Users/joeyarbrough/Projects/challenges/amazon-micro-services/applications/shipments/build/libs/shipments-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}