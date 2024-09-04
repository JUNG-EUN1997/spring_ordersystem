provider "aws" {
  region = "ap-northeast-2" #원하는 AWS 리전
}

# EcC2 인스턴스 생성
# resource "aws_instance" "jungeun-inst" {
#   ami = "ami-05d2438ca66594916"
#   instance_type = "t2.micro"
#   key_name = "my-keypair"
#   tags = {
#     Name = "jungeun-inst"
#   }
# }

# 명령순서
# main.tf 만들면 나머지는 명령어 입력할 때 마다 자동으로 생성된당
# 1. terraform 설치 : ⭐내용이 바뀔 때 마다 init⭐
#    terraform init
# 2. apply 하기
#    terraform apply
#       Enter a value: yes 

# - 삭제하기
#   terraform destroy
#       Enter a value: yes 


# terraform 구분 후, 어느 폴더에 다른 tf가 있는지 알려주면 된당
module "ec2_instance" {
  source = "./modules/ec2"
}

module "s3_bucket_policy" {
  source = "./modules/s3"
}