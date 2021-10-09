pipeline{
	agent any;
	stages{
		stage('Clone Project'){
			steps{
				git branch: 'main', url: 'https://github.com/ShadiFursa/FinalJenkins.git'
			}
		}
		stage('Build Project'){
			steps{	
    				sh "./gradlew clean build"

			}
		}
		stage('Run'){
            		steps{
                		sh 'JENKINS_NODE_COOKIE=dontkill java -jar ./build/libs/JenkinsFinalTask-1.0-SNAPSHOT.jar &'

            		}
        	}	
	}
}
