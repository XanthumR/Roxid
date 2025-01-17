package msku.ceng.madlab.roxid.voice;

import io.agora.media.RtcTokenBuilder2;
import msku.ceng.madlab.roxid.apiKeys;


public class TokenBuilder {
    // Need to set environment variable AGORA_APP_ID


    static String appId = apiKeys.getAppId();
    // Need to set environment variable AGORA_APP_CERTIFICATE
    static String appCertificate = apiKeys.getAppCertificate();

    static int tokenExpirationInSeconds = 3600;
    static int privilegeExpirationInSeconds = 3600;
    static int joinChannelPrivilegeExpireInSeconds = 3600;
    static int pubAudioPrivilegeExpireInSeconds = 3600;
    static int pubVideoPrivilegeExpireInSeconds = 3600;
    static int pubDataStreamPrivilegeExpireInSeconds = 3600;

    public String createToken(String channelName,String account){
        RtcTokenBuilder2 token = new RtcTokenBuilder2();

        String result = token.buildTokenWithUid(appId, appCertificate, channelName, 0, tokenExpirationInSeconds, joinChannelPrivilegeExpireInSeconds,
                pubAudioPrivilegeExpireInSeconds, pubVideoPrivilegeExpireInSeconds, pubDataStreamPrivilegeExpireInSeconds);

        return result;
    }

}