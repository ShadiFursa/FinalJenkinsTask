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
				script{
					"./gradlew build"
				}
			}
		}	

	}

}
