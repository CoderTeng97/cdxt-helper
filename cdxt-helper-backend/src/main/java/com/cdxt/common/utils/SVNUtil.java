package com.cdxt.common.utils;

import com.cdxt.common.exception.ResponseCommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.*;


/*/**
 * @Author: TengGang
 * @Date: 2020/6/20 11:41
 * @Description:  SVN工具类
 */
@Slf4j
public class SVNUtil {
    private String username;
    private String password;

    private ISVNAuthenticationManager authManager;

    public SVNUtil() {
    }

    public SVNUtil(String username, String password) {
        authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password.toCharArray());
    }


    /**
     * 检查是否存在文件
     *
     * @param src 文件路径
     * @return
     */
    public Boolean isExistFile(String src) {
        Boolean isExist = false;
        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(src));
            repository.setAuthenticationManager(authManager);
            SVNNodeKind nodeKind = repository.checkPath("", -1);
            if (SVNNodeKind.FILE ==  nodeKind) {
                isExist = true;
            }
        } catch (SVNException e) {
            log.error("SVN 请求异常", e);
            isExist = false;
        }
        return isExist;
    }

    /**
     * 下载SVN文件
     *
     * @param repositoryPath
     * @param filePath
     * @throws IOException
     */
    public void downloadFile(String repositoryPath,String filePath, String savePath) throws Exception  {
        SVNRepository repository = null;
        File file = new File(savePath+filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {

            //判断svn是否存在文件
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(repositoryPath));
            repository.setAuthenticationManager(authManager);
            SVNNodeKind nodeKind = repository.checkPath("", -1);
            if (SVNNodeKind.NONE !=  nodeKind) {
                SVNProperties svnProperties = new SVNProperties();
                repository.getFile(filePath, -1, svnProperties, fos);
            } else {
                throw new ResponseCommonException(HttpStatus.NOT_FOUND, "文件不存在,请确认是否存在");
            }
        }
    }

}
