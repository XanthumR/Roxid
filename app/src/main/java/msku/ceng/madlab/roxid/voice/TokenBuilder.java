package msku.ceng.madlab.roxid.voice;

import io.agora.media.RtcTokenBuilder2;


public class TokenBuilder {
    // Need to set environment variable AGORA_APP_ID
    static String appId = "29f451bf225e413380ebfb0e767a2fad";
    // Need to set environment variable AGORA_APP_CERTIFICATE
    static String appCertificate = "45003fb550bf425bac72cf374c16f8b9";

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