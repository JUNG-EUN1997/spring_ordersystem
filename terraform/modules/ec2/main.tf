# EcC2 인스턴스 생성
resource "aws_instance" "jungeun-inst" {
  ami = "ami-05d2438ca66594916"
  instance_type = "t2.micro"
  key_name = "my-keypair"
  tags = {
    Name = "jungeun-inst"
  }
}