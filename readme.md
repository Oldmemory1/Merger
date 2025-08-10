# 合并工具

## 项目功能：一个GUI程序，允许将使用hexEditor等工具拆分好的3个二进制bin文件合并为一个zip文件，可以输出到用户指定的路径。

## 项目作用：将已拆分好的可以逃避网盘审查的bin文件合并为完整的文件

## 项目配置文件 src\main\settings.properties 用于调整设置，file1_name，file2_name，file3_name指定源二进制文件名称，output_file_name指定合并后的文件名称
## file1_name = part1.bin
## file2_name = part2.bin
## file3_name = part3.bin
## output_file_name = resource.zip

## 运行环境：Java17.0.8 项目支持使用maven进行打包，且可以使用Launch4J等打包为exe文件

