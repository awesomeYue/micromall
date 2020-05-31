package com.mmall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class FTPUtil {

  private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

  private static final String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
  private static final int ftpPort = Integer.valueOf(PropertiesUtil.getProperty("ftp.server.port"));
  private static final String ftpUser = PropertiesUtil.getProperty("ftp.user");
  private static final String ftpPwd = PropertiesUtil.getProperty("ftp.pwd");

  private String ip;
  private int port;
  private String user;
  private String pwd;
  private FTPClient client;

  private FTPUtil(String ip, int port, String user, String pwd) {
    this.ip = ip;
    this.port = port;
    this.user = user;
    this.pwd = pwd;
  }

  /**
   * 上传文件
   *
   * @param files
   * @return
   * @throws IOException
   */
  public static boolean uploadFile(List<File> files) throws IOException {
    FTPUtil ftpUtil = new FTPUtil(ftpIp, ftpPort, ftpUser, ftpPwd);
    logger.info("开始连接ftp服务器,开始上传");
    boolean result = ftpUtil.uploadFile("img", files);
    logger.info("开始连接ftp服务器,结束上传,上传结果:{}", result);
    return result;
  }

  /**
   * 将文件上传至ftp服务器
   *
   * @param remotePath 远程的路径
   * @param files 上传的文件集合
   * @return 返回true上传成功，false上传失败
   * @throws IOException
   */
  private boolean uploadFile(String remotePath, List<File> files) throws IOException {
    boolean uploaded = false;
    FileInputStream fis = null;
    if (connectServer(this.ip, this.port, this.user, this.pwd)) {
      try {
        this.client.changeWorkingDirectory(remotePath);
        this.client.setBufferSize(1024);
        this.client.setControlEncoding("UTF-8");
        // 设置上传文件的类型为二进制，防止乱码
        this.client.setFileType(FTPClient.BINARY_FILE_TYPE);
        // 打开本地的被动模式
        this.client.enterLocalPassiveMode();
        for (File file : files) {
          fis = new FileInputStream(file);
          this.client.storeFile(file.getName(), fis);
          uploaded = true;
        }
      } catch (IOException e) {
        logger.error("上传文件异常", e);
      } finally {
        fis.close();
        this.client.disconnect();
      }
    }
    return uploaded;
  }

  /**
   * 连接ftp服务器
   *
   * @param ip 地址
   * @param port 端口
   * @param user 用户名
   * @param pwd 密码
   * @return true连接成功，false连接失败
   */
  private boolean connectServer(String ip, int port, String user, String pwd) {
    boolean isSuccess = false;
    client = new FTPClient();
    try {
      client.connect(ip, port);
      isSuccess = client.login(user, pwd);
    } catch (IOException e) {
      logger.error("连接FTP服务器异常", e);
    }
    return isSuccess;
  }
}
