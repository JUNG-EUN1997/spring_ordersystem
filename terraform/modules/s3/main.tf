# aws_s3_bucket은 정해져 있는 값, 버킷명인 "jungeun-bucket-0521"은 중복불가
resource "aws_s3_bucket" "jungeun-bucket-0521" { 
  bucket = "jungeun-bucket-0521"
}