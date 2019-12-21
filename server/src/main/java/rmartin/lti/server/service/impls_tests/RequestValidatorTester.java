//package rmartin.lti.server.service.impls_tests;
//
//import rmartin.lti.server.service.impls.RequestValidatorImplCustomOauth;
//
//import java.util.HashMap;
//
//public class RequestValidatorTester {
//    public static void main(String[] args) {
//        var target = new RequestValidatorImplCustomOauth();
//        var map = new HashMap<String, String[]>();
//
//        map.put("status", new String[]{"Hello Ladies + Gentlemen, a signed OAuth request!"});
//        map.put("include_entities", new String[]{"true"});
//        map.put("oauth_consumer_key", new String[]{"xvz1evFS4wEEPTGEFPHBog"});
//        map.put("oauth_nonce", new String[]{"kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg"});
//        map.put("oauth_signature_method", new String[]{"HMAC-SHA1"});
//        map.put("oauth_timestamp", new String[]{"1318622958"});
//        map.put("oauth_token", new String[]{"370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb"});
//        map.put("oauth_version", new String[]{"1.0"});
//
//        var expected = "include_entities=true&oauth_consumer_key=xvz1evFS4wEEPTGEFPHBog&oauth_nonce=kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1318622958&oauth_token=370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb&oauth_version=1.0&status=Hello%20Ladies%20%2B%20Gentlemen%2C%20a%20signed%20OAuth%20request%21";
//        System.out.println(target.getParamString(map).equals(expected));
//
//        var expected2 = "POST&https%3A%2F%2Fapi.twitter.com%2F1.1%2Fstatuses%2Fupdate.json&include_entities%3Dtrue%26oauth_consumer_key%3Dxvz1evFS4wEEPTGEFPHBog%26oauth_nonce%3DkYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1318622958%26oauth_token%3D370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb%26oauth_version%3D1.0%26status%3DHello%2520Ladies%2520%252B%2520Gentlemen%252C%2520a%2520signed%2520OAuth%2520request%2521";
//        System.out.println(target.getSignatureString("pOsT", "https://api.twitter.com/1.1/statuses/update.json", map).equals(expected2));
//
//        String secret = "kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw";
//        var expected3 = "hCtSmYh+iHYCEqBWrE7C7hYmtUk=";
//        System.out.println(target.getSignature(target.getSignatureString("pOsT", "https://api.twitter.com/1.1/statuses/update.json", map), secret).equals(expected3));
//    }
//}
