def attachments = [
  [
    text: 'I find your lack of faith disturbing!',
    fallback: 'Hey, Vader seems to be mad at you.',
    color: '#ff0000'
  ]
]

def slackResponse = slackSend(channel: "#JenkinsFinalProject", attachments: attachments)
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
		stage('Run'){
            		steps{
                		sh 'JENKINS_NODE_COOKIE=dontkill java -jar ./build/libs/JenkinsFinalTask-1.0-SNAPSHOT.jar &'

            		}
        	}
		        stage('Send Slack'){
            steps {
                slackSend(channel: slackResponse.channelId, message : 'The app is running', timestamp: slackResponse.ts)
            }
        }
	}
}
