
# Prerequisites :
aws cli have to be configured for aws IAM user AndriiG
Account ID : 415800032746

configure docker registry:

aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin 415800032746.dkr.ecr.us-west-2.amazonaws.com
