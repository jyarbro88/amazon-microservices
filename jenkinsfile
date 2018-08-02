pipeline {
    agent any

    environment {
      CFAPI = 'https://api.run.pivotal.io'
      CFUSERNAME = credentials('jyarbrough@solstice.com')
      CFPASS = credentials('Pivotal123!!')
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building Application: accounts...'
                sh './gradlew applications/accounts:build.gradle'
                echo 'Building Application: orders...'
                sh './gradlew applications/orders:build.gradle'
                echo 'Building Application: products...'
                sh './gradlew applications/products:build.gradle'
                echo 'Building Application: shipments...'
                sh './gradlew applications/shipments:build.gradle'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Logging in to CF...'
                sh 'cf login -a $CFAPI -u $CFUSERNAME -p $CFPASS -o solstice-org -s jyarbrough-cnt'
                echo 'Deploying....'

                sh 'cf push accounts -t 180 -p applications/accounts/build/libs/accounts-0.0.1-SNAPSHOT.jar'
                sh 'cf push orders -t 180 -p applications/orders/build/libs/orders-0.0.1-SNAPSHOT.jar'
                sh 'cf push products -t 180 -p applications/products/build/libs/products-0.0.1-SNAPSHOT.jar'
                sh 'cf push shipments -t 180 -p applications/shipments/build/libs/shipments-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}