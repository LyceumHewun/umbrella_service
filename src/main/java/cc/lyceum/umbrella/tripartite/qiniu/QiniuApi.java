package cc.lyceum.umbrella.tripartite.qiniu;

import com.qiniu.util.Auth;

import static cc.lyceum.umbrella.tripartite.qiniu.QiniuConfig.ACCESS_KEY;
import static cc.lyceum.umbrella.tripartite.qiniu.QiniuConfig.BUCKET;
import static cc.lyceum.umbrella.tripartite.qiniu.QiniuConfig.SECRET_KEY;

public class QiniuApi {

    private Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    /**
     * @return 上传凭证
     */
    public String getUploadToken() {
        return auth.uploadToken(BUCKET);
    }
}
