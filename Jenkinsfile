def attachments = [
  [
    color: '#fff1E0'
  ]
]

def slackResponse = slackSend(channel: "#JenkinsFinalProject", attachments: attachments)

pipeline{
	agent any;
	stages{
		stage('Clone Project'){
			steps{
				git branch: 'main', url: 'https://github.com/ShadiFursa/FinalJenkinsTask.git'

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
