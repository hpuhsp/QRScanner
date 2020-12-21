package com.hnsh.scanner.zbarUtils;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Description:
 * @Author: Hsp
 * @Email: 1101121039@qq.com
 * @CreateTime: 2020/9/25 10:29
 * @UpdateRemark:
 */
public class Constants {

    public static final String ARG_PRODUCT_ID = "product_id";
    public static final String ARG_PRODUCT_NAME = "product_name";

    public static final String ARG_COMMENT_PRODUCT = "comment_product";
    public static final String ARG_COMMENT_TYPE = "comment_type";
    public static final String ARG_COMMENT_ID = "comment_id";
    public static final String ARG_COMMENT_RATTING = "comment_ratting";

    public static final String ARG_FEED_ID = "feed_id";
    public static final String ARG_FEED_AUTHOR_ID = "feed_author_id";

    public static final String UPDATE_FEED = "update_edit";

    public static final String ARG_FEED_TYPE = "feed_type";
    public static final String ARG_DYNAMIC_ITEM_TYPE = "dynamic_list_item_type";

    public static final String ARG_FORWARD_INTENT = "intent_forward_model";

    public static final String USER_EDIT_SIGNATURE = "edit_signature";

    public static final String HOME_BEER_TOP_TYPE = "beer_top_type";

    //上传新酒部分的传递静态字段
    public static final String UPLOAD_BAR_CODE = "upload_bar_code";
    public static final String CHECK_BEER_BARCODE = "check_beer_bar_code";

    //带返回code的intent跳转静参
    public static final int COMMENT_EDIT_RESPONS_CODE = 101;
    public static final int COMMENT_DETAIL_EDIT_RESPONS_CODE = 102;
    public static final int COMMENT_LIST_TO_FEEDDETAIL_CODE = 108;
    public static final int RESULT_FOR_NEW_WINE_LIST_CODE = 1019;
    public static final int RESULT_FOR_REEDIT_WINE_LIST_CODE = 1020;
    public static final int RESULT_FOR_MULTI_LIST_BUID_ACTION = 1021;

    //扫码返回条形码对应内容静参
    public static final String CHECK_BAR_FOR_RESULT_CODE = "check_for_result";
    public static final String SCAN_BAR_CODE_RESULT = "barcode_result";

    /**
     * web_view静参
     */
    public static final String WEBVIEW_LOAD_URL = "weburl";
    public static final String IS_DEBUG_MODE = "isDebugMode";
    public static final String IS_BG_TRANSPARENT = "bgTransparent";

    /**
     * 用户信息中心
     */
    public static final String USER_OLD_SIGN = "old_sign";
    public static final String LETTER_USER_SESSION_ID = "session_id";

    // 用户信息缓存Key
    public static String USER_AUTH_KEY = "auth_key";
    public static String USER_ID_KEY = "id_key";
    public static String USER_AVATAR_KEY = "avatar_key";
    public static String USER_TEL_KEY = "tel_key";
    public static String USER_BINDED_KEY = "binded_key";
    public static String USER_NICKNAME_KEY = "nickname_key";
    public static String USER_SIGNATURE_KEY = "signature_key";
    public static String USER_GENDER_KEY = "gender_key";
    public static String USER_BIRTHDAY_KEY = "birthday_key";
    public static String USER_REGION_KEY = "region_key";
    public static String USER_FOLLOWER_KEY = "follower_key";
    public static String USER_FOLLOWING_KEY = "following_key";
    public static String USER_FEEDCNT_KEY = "feed_key";
    public static String USER_CREATE_TIME_KEY = "create_time_key";
    public static String USER_DYNAMIC_COUNT_KEY = "dynamic_count_key";
    public static String USER_BACKGROUND_KEY = "user_background_key";
    // 用户权限
    public static String AUTHOR_PERMISSION_KEY = "author_permission_key";

    public static String USER_LOCATION_INFORMATION_CITY_NAME = "location_city_name_key"; // 位置信息城市名称
    public static String USER_LOCATION_INFORMATION_CITY_CODE = "location_city_code_key"; // 位置信息城市code
    public static String USER_LOCATION_LONGITUDE_VALUES = "location_longitude_values"; // 位置信息城市code
    public static String USER_LOCATION_LATITUDE_VALUES = "location_latitude_values"; // 位置信息城市code

    public static String RULE_BEERCOMMENT_KEY = "rule_beercomment_key";
    public static String RULE_BARCOMMENT_KEY = "rule_barComment_key";
    public static String RULE_TOPIC_KEY = "rule_topic_key";
    public static String RULE_BEERTAG_KEY = "rule_beertag_key";
    public static String RULE_QUALITY_COMMENT_KEY = "rule_quality_comment_key";
    // 显示广告的开始时间和结束时间
    public static String GUIDE_START_TIME = "guide_start_time";
    public static String GUIDE_END_TIME = "guide_end_time";
    public static String GUIDE_IMAGE_LINK = "guide_image_link";
    public static String GUIDE_TIME_DURATION = "guide_time_delay";
    public static String GUIDE_IMAGE_URL = "guide_image_url";

    // 是否登陆缓存标志Key
    public static String IS_LOGIN_KEY = "is_login_key";

    public static String BLOCKS_TAB_API = "tab_api";
    public static String BLOCKS_TAB_TYPE = "tab_type";

    /**
     * 完善标签完善的类型
     */
    public static final String ARG_PERFECT_DESC_TYPE = "fill_desc";
    public static final String ARG_PERFECT_TAG_TYPE = "fill_tag";
    /**
     * 用户是否关注的三种状态
     */
    public static String FOLLOWER_STATE_TYPE = "follower";
    public static String UNFOLLOW_STATE_TYPE = "unfollow";
    public static String SELF_STATE_TYPE = "self";
    /**
     * 用户关注的六个页面类型
     */
    public static String TYPE_OF_HEAD_LINE_FOLLOW = "headline_followed"; // 头条的关注
    public static String TYPE_OF_BEER_DETAIL_FOLLOW = "beer_details_followed"; // 啤酒详情页的关注
    public static String TYPE_OF_FEED_DETAIL_FOLLOW = "feed_details_followed"; // feed详情页的关注
    public static String TYPE_OF_TOPIC_DETAIL_FOLLOW = "topic_details_followed"; // 话题详情页的关注
    public static String TYPE_OF_FOLLOW_LIST_FOLLOW = "follow_list_followed"; // 关注和粉丝列表的关注
    public static String TYPE_OF_USER_HOME_FOLLOW = "user_home_followed"; // 个人中心页面的关注
    public static String TYPE_OF_ADD_FRIEND_FOLLOW = "add_friend_followed"; // 添加好友页面的关注

    /**
     * 用户邀请码
     */
    public static String USER_INVITE_SUM = "user_invitesum";
    public static String USER_INVITE_COUNT = "user_curcount";
    public static String USER_INVITE_CODE = "user_invitecode";
    public static String USER_INVITE_VAULE = "user_hotvaule";
    public static String USER_INVITE_REMAIN_COUNT = "user_remaincnt";
    public static String USER_INVITE_RANG = "user_range";

    public static String TOPIC_JING_TAG_NAME = "cn_jing";
    public static String TOPIC_QUESTION_TAG_NAME = "cn_question";
    public static String TOPIC_HOT_TAG_NAME = "cn_hot";
    public static String TOPIC_FIRST_TAG_NAME = "cn_first";

    public static String BEER_COMMENT_PRAISED_TYPE = "beer_praised_type";
    public static String BAR_COMMENT_PRAISED_TYPE = "beer_praised_type";
    public static String STONE_POSTER_FILENAME = "stone_share_poster";

    /**
     * 用户信息更新类型
     */
    public enum UserInfoUpdateType {
        UPDATE_AVATAR,
        UPDATE_TEL,
        UPDATE_NICKLE,
        UPDATE_GENDER,
        UPDATE_BIRTHDAY,
        UPDATE_SIGNATURE,
        UPDATE_REGION,
        UPDATE_BACKGROUND
    }

    /**
     * 用户登陆类型
     */
    public enum UserLoginType {
        WEIXIN_LOGIN,
        EMAIL_LOGIN,
        TEL_LOGIN,
    }

    // Feed的类型
    public static final String FEED_TYPE_TWEET_NEWS = "TWEET_NEWS";
    public static final String FEED_TYPE_COMMENT = "COMMENT_BEER";
    public static final String FEED_TYPE_REPOST = "REPOST";
    public static final String FEED_TYPE_TOPIC = "TOPIC";
    public static final String FEED_TYPE_BAR = "COMMENT_BAR";
    public static final String FEED_TYPE_NEWS = "COMMENT_NEWS";
    public static final String FEED_TYPE_BAR_SIGNIN = "BAR_SIGNIN"; // 酒吧签到类型
    public static final String FEED_TYPE_POINT_CONSUME = "POINT_CONSUME";
    public static final String FEED_TYPE_HOT_CONSUME = "HOT_CONSUME";
    public static final String FEED_TYPE_UPLOAD_BEER = "UPLOAD_BEER";
    public static final String FEED_TYPE_COMMUNITY = "COMMUNITY";


    // 举报的类型
    public static final String REPORT_TPEY_USER = "USER";
    public static final String REPORT_TPEY_FEED = "FEED";
    public static final String REPORT_TPEY_REPLY = "REPLY";
    public static final String REPORT_TPEY_TOPIC = "TOPIC";
    public static final String REPORT_TPEY_REPLY_TOPIC = "REPLY_TOPIC";
    public static final String REPORT_TPEY_REPLY_ACTIVITY = "REPLY_ACTIVITY";
    public static final String REPORT_TPEY_COMMUNITY = "COMMUNITY";
    public static final String REPORT_TYPE_TAG_DETAIL = "LABEL";

    // 消息常量
    public static final String ALIAS_UID_TYPE = "jiuhuar";
    public static final String MSG_PRAISE_TYPE = "PRAISE";
    public static final String MSG_COMMENT_TYPE = "COMMENT";
    public static final String MSG_REPOST_TYPE = "REPOST";
    public static final String MSG_SYSTEM_TYPE = "SYSTEM";
    public static final String MSG_LETTER_TYPE = "LETTER";
    public static final String MSG_FOLLOWER_TYPE = "FOLLOWER";
    public static final String MSG_REMIND_TYPE = "REMIND";
    public static final String MSG_BADGE_TYPE = "USER_BADGE_LIST"; // 徽章类型
    public static final String MSG_BAR_NEW_BEER_TYPE = "BAR_NEW_BEER"; // 酒吧上新类型

    public static final String MSG_TEXT_KEY = "text";


    public static final String EXTRA_MSG_BUNDLE = "extra_msg_bundle";
    public static final String EXTRA_GUIDE_LINK = "extra_guide_link";

    // 通知类型
    public static final String NOTICE_BEER_DETAIL = "BEER_DETAIL";
    public static final String NOTICE_UPLOAD_BEER_DETAIL = "UPLOAD_BEER_DETAIL";
    public static final String NOTICE_H5 = "H5";
    public static final String NOTICE_ME = "ME";
    public static final String NOTICE_BEER_LIST = "BEER_LIST";
    public static final String NOTICE_TOPIC_DEATIL = "TOPIC_DEATIL";

    /**
     * 字数限制
     */
    public static final int DYNAMIC_TEX_MAX_COUNT = 2000; // 动态最大字数限制
    public static final int USERINFO_SIGN_EDITTEXT_MAX_COUNT = 30;  // 用户信息编辑最大字数限制
    public static final int USER_PASSWORD_LENTH_LIMIT = 16; //用户密码字数限制
    public static final int FEED_DETAIL_REPLY_EDIT_LIMIT = 144; // feed 回复文本字数限制
    public static final int NEWS_COMMENT_EDIT_LIMIT = 1000; // 资讯点评字数限制

    // APP登录类型
    public static final String APP_LOGIN_TYPE = "logintype";
    public static final String WEIXIN_LOGIN_TYPE = "weixin";//登录类型
    public static final String TEL_LOGIN_TYPE = "tel";
    // 5.3.0版本绑定手机号相关
    public static final String BIND_TYPE = "bind_type"; // 手机号绑定的类型
    public static final String WX_BIND_TEL = "wx_bind_tel"; // 微信登录账户管理绑定手机号(前提没有绑定)
    public static final String CHANGE_WX_BINDING = "change_wx_binding"; // 更换微信绑定手机号
    public static final String CHANGE_TEL_BINDING = "change_tel_binding"; // 更换手机账号
    public static final String BIND_ACCOUNT_ENTITY = "bind_entity";
    public static final String BINDING_NUMBER = "binding_number"; // 已绑定手机号
    public static final String NUMBER_FOR_RESPONSE = "response_number"; // setResult返回标识

    public static final String CHAT_TYPEP2P_NOTIFICATION = "chatTypeP2P";

    public static final String UPDATED_TIME_KEY = "updated_time_key"; // 弹更新窗口的时间key
    public static final String JIUHUAR_NEW_VERSION_KEY = "app_new_version"; // 有新更新提示缓存新版本

    // 分享的类型区分标识
    public static final int SHARE_BEER_PRO_COMMENT_TYPE = 1661; // 复杂点评
    public static final int SHARE_BEER_SIMPLE_COMMENT_TYPE = 1662; // 简单点评
    public static final int SHARE_BAR_COMMENT_TYPE = 1663; // 简单点评
    // 展示啤酒列表所有静态参数，分类酒单和酒库类型酒单列表
    public static final String ARG_INTENT_EXTRA_LINK = "api"; // 接口路径
    public static final String ARG_INTENT_EXTRA_TITLE = "title"; // 路由标题
    public static final String ARG_INTENT_EXTRA_HIDEHEADER = "hideHeader"; // 是否显示的筛选头部
    public static final String ARG_INTENT_EXTRA_OID = "oid"; // 路由ID
    public static final String ARG_INTENT_EXTRA_TYPE = "type"; // 路由ID
    public static final String ARG_INTENT_BAR_ID = "barId"; // 酒吧ID，酒吧瓶啤分类
    public static final String API_HIDEHEADER = "beer-category/beers"; // 啤酒分类API
    public static final String ARG_TYPE_SCOPE = "scope"; // 是否显示头部TAB 0代表是单一风格、1代表是所属风格的统类
    public static final String ARG_BEER_TYPE = "beerType"; // 啤酒筛选列表下的啤酒类型

    // 四种排序规则的筛选条件
    public static final String RULE_OF_HIGH_SCORE = "highestRating";
    public static final String RULE_OF_MOST_COMMENT = "mostComment";
    public static final String RULE_OF_NEWEST_UPLOAD = "newestShelf"; // 5.8.0 版本删除最新收录
    public static final String RULE_OF_NEWST_COMMENT = "newestComment";
    public static final String RULE_OF_BEER_LIBRARY = "library";
    public static final String RULE_OF_HIGH_ASSOCIATED = "highestCorrelation";

    //==================酒吧页面相关静态标识==========================//
    public static final String SIGNED_UP_USER_COUNT = "signed_up_cnt"; // 酒吧报名人数
    public static final String ARG_BAR_EVENT_TRANS_KEY = "bar_trans_model"; // 酒吧的ID
    public static final String ARG_ID_KEY = "oid"; // 酒吧的ID
    public static final String ARG_BAR_ACT_APPLY_ID = "id"; // 活动报名详情
    public static final String TYPE_OF_LIST = "type_of_list"; // 酒吧详情页混合列表的数据类型
    public static final int BAR_EVENT_SIGNED_LIST = 1091; // 酒吧活动报名
    public static final int BAR_SIGN_IN_LIST = 1092; // 酒吧签到
    public static final int BAR_EVENT_CARD_LIST = 1093; // 酒吧活动列表
    public static final String BAR_EVENT_OID = "bar_event_oid"; // 酒吧活动的ID
    // 我的活动列表在进行和已经结束
    public static final String ARG_USER_EVENT_GOING = "ONGOING";
    public static final String ARG_USER_EVENT_OVER = "OVER";

    // ===================下面是4.0用户中心GridMenuItem 的类型=========================
    public static final String MY_COMMENT = "my_comments";
    public static final String MY_TOPICS = "my_topics";
    public static final String MY_COMMUNITY = "my_community";
    public static final String MY_EVENTS = "my_events";
    public static final String MY_BEER_LIST = "my_beer_list";
    public static final String MY_BADGES = "my_badge";
    public static final String MY_MESSAGES = "mg_messages";
    public static final String MY_COLLECTIONS = "my_collections";
    public static final String INVITE_FRIENDS = "invite_friends";
    public static final String MY_ADDRESS = "my_address";
    public static final String MY_COUPONS = "my_coupons"; // 我的优惠券
    public static final String SCORE_STORE = "score_store"; // 积分商城
    public static final String MY_STONE_VIP = "my_stone"; // STONE会员商城
    //------------------------4.2.0--添加订单信息--------------------
    public static final int WAIT_TO_PAY = 0; // 待付款
    public static final int WAIT_TO_SEND = 1; // 待发货
    public static final int WAIT_TO_RECEIVE = 2; // 待收货
    public static final int ORDERS_FINISHED = 3; // 已完成
    public static final int ORDERS_CANCELD = 4; // 已取消
    public static final int ALL_ORDERS = 5; // 所有类型
    //=========================APP首次进入需要显示的内容,红点或者蒙板==============================
    public static String BOTTLE_BEER_FIRST_SHOW_KEY = "bottle_beer_first_show_key"; // APP首次进入
    public static String DRAFT_BEER_FIRST_SHOW_KEY = "bottle_beer_first_show_key"; // APP生啤首次进入

    public static String BEER_STYLE_FIRST_SHOW_KEY = "beer_style_first_show_key"; // APP首次进入
    public static String BEER_BRAND_FIRST_SHOW_KEY = "beer_brand_first_show_key"; // APP首次进入
    //=========================搜索的几种相关类型=============================
    public static String ALL_SEARCH_TYLE = "search_type";
    public static String DEFAULT_SEARCH_TYPE = "default_search_type";
    public static String SEARCH_BEER_TYPE = "search_beer";
    public static String SEARCH_BEER_SELECT_TYPE = "search_beer_select";
    public static String SEARCH_USER_TYPE = "search_user";
    public static String SEARCH_TAG_TYPE = "search_tag";
    public static String SEARCH_TOPIC_TYPE = "search_topic";
    public static String SEARCH_BAR_TYPE = "search_bar";
    public static String SEARCH_NEWS_TYPE = "search_news";
    //====================4.7.0关于STONE==================================
    // 类型区分Stone、Jiuhuar
    public static final String APP_PRODUCT_BRAND = "app_product_type";
    public static final String JIUHUAR_MALL = "JIUHUAR";
    public static final String STONE_MALL = "STONE";
    // 是否需要返回上个页面
    public static final String MALL_HOME_INTENT = "stone_home_intent";

    //=======================5.2.0版本新增生啤类型==============
    public static final String ALL_BEER_TYPE = "ALL"; // 所有的啤酒类型
    public static final String BOTTLE_BEER_TYPE = "BOTTLE"; // 瓶啤类型
    public static final String DRAFT_BEER_TYPE = "DRAFT"; // 生啤类型

    public static final int RESULT_REQUEST_REPLAY_OK = 1001; // 回复Feed相应


    public static final String POSTER_BEER_TYPE = "beer"; // 海报啤酒类型
    public static final String POSTER_ACT_TYPE = "activity"; // 海报活动类型

    public static final int GENDER_MAN_TYPE = 1; // 男
    public static final int GENDER_WOMAN_TYPE = 2; // 女

    public static final String INTENT_ADD_PLACE = "intent_add_place";

    //***********************5.4.0-版本添加滤镜效果相关***********************************
    public static final String DRINK_USER_KEY = "drink_user"; // 在那和model key
    public static final String DRINK_BEER_KEY = "drink_beer"; // 在那和model key
    public static final String DRINK_PLACE_KEY = "drink_palce"; // 在那和model key
    /**
     * APP-5.6.0添多语言类型支持
     */
    public static final String LOCALE_LANGUAGE = "locale_language";
    public static final String LOCALE_COUNTRY = "locale_country";
    public static final String STORE_NOTIFICATION = "notification_state";
    public static final String CLIENT_LANGUAGE = "client.language";
    // 商城消息通知开关状态
    public static final String OPEN_STATE = "open";
    public static final String CLOSE_STATE = "close";

    /**
     * 图片编辑滤镜的风格
     */
    public enum FilterStyle {
        DEFAULT, LIGHT_TIME, SEA_WIND, FOREST_WIND, CHOCOLATE, EXAMPLE_ONE, MORE_EXAMPLE
    }

    /**
     * 图片标签的类型
     */
    public enum TagType {
        BEER_TAG,
        PLACE_TAG,
        BAR_TAG,
        USER_TAG
    }

    /**
     * 图片编辑常规比例
     * RATIO_SQUARE_NO -- 占位用
     * 目前只有
     * 1-(1:1),2-(3:4),3-(3:2)
     */
    public enum PicSize implements Parcelable {
        RATIO_SQUARE_NO,
        RATIO_SQUARE,
        RATIO_3_4,
        RATIO_3_2;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<PicSize> CREATOR = new Creator<PicSize>() {
            @Override
            public PicSize createFromParcel(Parcel in) {
                return PicSize.values()[in.readInt()];
            }

            @Override
            public PicSize[] newArray(int size) {
                return new PicSize[size];
            }
        };

    }


    /**
     * 以下是引导蒙板工具类需要枚举类型
     */
    // 定义GuideView相对于targetView的方位，共八种。不设置则默认在targetView下方
    public enum MyDirection {
        LEFT, TOP, RIGHT, BOTTOM,
        LEFT_TOP, LEFT_BOTTOM,
        RIGHT_TOP, RIGHT_BOTTOM
    }

    //定义目标控件的形状，共3种。圆形，椭圆，带圆角的矩形（可以设置圆角大小），不设置则默认是圆形
    public enum MyShape {
        CIRCULAR, ELLIPSE, RECTANGULAR
    }

    public static final String TAG_BEER_TASTE_TYPE = "taste"; // 外观
    public static final String TAG_BEER_FEEL_TYPE = "feel"; // 味道
    public static final String TAG_BEER_GROUP_TYPE = "group"; // 口感
    public static final String TAG_BEER_STYLE_TYPE = "style"; // 总体感受

//    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654); // 上海市经纬度

    public static final String STATUS_TYPE_OF_DEL = "DELETED"; // 酒吧删除状态


    public static final int REQUEST_CODE_SETTING = 0x39; // 系统设置跳转Code

//    public final static String FILE_PROVIDER = JiuHuaRApplication.getInstance().getPackageName() + ".fileprovider"; // 适配7.0文件缓存

    /**
     * 全局路由相关静态路径参数-分类(pathUri)
     * 所有路由添加是否需要登录的参数：needLogin=0/1
     * H5路由增加由  积分商城或者普通H5控制器打开 判断：beergeek://jiuhuar.com/h5?url=xxx&title=xxx&hotstore=0/1
     */
    public static final String ROUTER_OF_BEER_LIBRARY = "/library"; // 精酿酒库
    public static final String ROUTER_OF_UPLOAD_BEER = "/uploadbeer"; // 上传新酒
    public static final String ROUTER_OF_REUPLOAD_BEER = "/uploadbeer?oid=xxx"; // 重新编辑上传新酒资料(与上一条一致，增加oid参数)
    public static final String ROUTER_OF_INVITE_DIALOG = "/invite"; // web页面内链接邀请好友弹框(分享弹框)
    public static final String ROUTER_OF_INVITE_FRIENDS = "/inviteH5"; // 邀请好友
    public static final String ROUTER_OF_BEER_DETAILS = "/beerdetail"; // 啤酒详情
    public static final String ROUTER_OF_FEED_DETAILS = "/feeddetail"; // 动态详情
    public static final String ROUTER_OF_COMMENT_DETAILS = "/commentdetail"; // 点评详情
    public static final String ROUTER_OF_USER_INDEX = "/user"; // 个人中心(用户主页)
    public static final String ROUTER_OF_NORMAL_WEB_PAGE = "/h5"; // H5页面
    public static final String ROUTER_OF_SENTIMENT_STORE = "/store"; // 人气商城
    public static final String ROUTER_OF_STAR_STORE = "/hotstore"; // 积分商城
    public static final String ROUTER_OF_BBS_LIST = "/bbslist"; // 精酿圈显示列表(圈子列表)
    public static final String ROUTER_OF_BEER_WALL = "/beerWall"; // 酒墙
    public static final String ROUTER_OF_BBS_DETAILS = "/bbsdetail"; // 精酿圈详情(圈子话题列表)
    public static final String ROUTER_OF_TOPIC_DETAILS = "/post"; // 话题详情(显示楼层),此处省去跳楼操作的路由
    public static final String ROUTER_OF_USER_CENTER = "/me"; // APP用户中心页面
    public static final String ROUTER_OF_TOP_BEER_LIST = "/beerlist"; // 评分最高等啤酒列表
    public static final String ROUTER_OF_USER_LOGIN = "/login"; // 登录
    public static final String ROUTER_OF_SCAN_BARCODE = "/scan"; // 扫条码
    public static final String ROUTER_OF_USER_SIGN = "/signin"; // 用户签到
    public static final String ROUTER_OF_USER_BEER_LIST = "/userbeerlist"; // 用户酒单页面
    public static final String ROUTER_OF_BAR_DETAILS = "/bar"; // 酒吧详情
    public static final String ROUTER_OF_BAR_REVIEW = "/barreview"; // 酒吧点评详情页
    public static final String ROUTER_OF_BAR_LSIT = "/barlist"; // 酒吧列表
    public static final String ROUTER_OF_WEB_RECOMMENDED = "/recommend/review"; // 往期推荐列表
    public static final String ROUTER_OF_HOT_TOPICS = "/topic/hot"; // 热门话题列表
    public static final String ROUTER_OF_CRAFT_MALL = "/mallHome"; // 酒花儿商城的路由
    public static final String ROUTER_OF_PRODUCT_DETAIL = "/productdetail";
    public static final String ROUTER_OF_SHOPPING_CART = "/shoppingcart";
    public static final String ROUTER_OF_MALL_LIST = "/malllist";
    public static final String ROUTER_OF_MALL_STYLE = "/styleMallList";
    public static final String ROUTER_OF_MALL_BRAND = "/brandMallList";
    public static final String ROUTER_OF_MALL_FLASH_SALE = "/flashSaleDetail";
    public static final String ROUTER_OF_MALL_TOPIC = "/mallTopicDetail";

    public static final String ROUTER_OF_BEERLIST_DETAILL = "/beerList/detail";
    public static final String ROUTER_OF_NEWS_DETAIL = "/news";
    public static final String ROUTER_OF_BEERSTYLE_DETAIL = "/beerstyle";
    public static final String ROUTER_OF_BEERBRAND_DETAIL = "/beerbrand";
    // STONE的一些配置
    public static final String ROUTER_OF_STONE_PAGE = "/stone"; // 巨石相关页面路由标识
    public static final String ROUTER_OF_STONE_VIP_MEMBER = "/stone/vipmember"; // STONE会员页面
    public static final String ROUTER_OF_STONE_PRODUCT_DETAIL = "/stone/productdetail"; // STONE商品详情
    public static final String ROUTER_OF_STONE_PRODUCT_LIST = "/stone/productlist"; // STONE商品列表
    public static final String ROUTER_OF_STONE_SHOPPING_CART = "/stone/shoppingcart"; // STONE购物车
    public static final String ROUTER_OF_STONE_MEMBER_DESC = "/stone/vipintro"; // STONE会员介绍
    public static final String ROUTER_OF_STONE_HOME = "/stone/home"; // STONE商城首页
    // 5.4.0相关路由配置
    public static final String ROUTER_OF_TAG_DETAIL = "/tagdetail?name=";
    // 5.5.0 路由配置
    public static final String ROUTER_OF_MALL_STYLE_LIST = "/styleMallList"; // 商城风格列表
    public static final String ROUTER_OF_MALL_BRAND_LIST = "/brandMallList"; // 商城品牌列表

    //=============================***QueryParameter***================================
    public static final String ROUTER_ARG_LOGIN_CHECK = "needLogin"; // 路由登录判断字段，值为1或0
    public static final String ROUTER_ARG_STAR_STORE_CHECK = "hotstore"; // h5页面跳转到积分商城的校验字段，值为1或0
    public static final String KEY_INTENT_EXTRA_HOTSTORE = "hotstore"; // 是否是积分商城
    public static final String KEY_INTENT_EXTRA_SHARETYPE = "shareType"; // 分享类型
    public static final String KEY_INTENT_EXTRA_SHARETITLE = "shareTitle"; // 分享标题
    public static final String KEY_INTENT_EXTRA_SHARETEXT = "shareText"; // 分享描述
    public static final String KEY_INTENT_EXTRA_SHAREIMAGE = "shareImage"; // 分享图片
    public static final String KEY_INTENT_EXTRA_ACTION = "action";


    //图片裁剪
    public static final String CROP_IMAGE = "crop_image";
    public static final String CROP_IMAGE_PATH = "crop_image_path";

    //身份认证
    public static final String CERTIFICATION_TYPE = "certification_type";

    //关注状态
    public static final String FOLLOWER = "follower";//关注
    public static final String UN_FOLLOW = "unfollow";//未关注
    public static final String SELF = "self";//自己

    //网络设置
    public static final String SETTING_WIFI_AUTO_PLAY = "setting_wifi_auto_play";
    public static final String SETTING_CELLULAR_AUTO_PLAY = "setting_cellular_auto_play";

    // 视频相关
    public static final String VIDEO_RECORD_RESOLUTION = "resolution";
    public static final String VIDEO_RECORD_TYPE = "type";
    public static final String VIDEO_EDITER_PATH = "key_video_editer_path"; // 路径的key

    //获取社区信息流类型（最近发布，最近回复，推荐）
    public static final String COMMUNITY_LAST_POST = "lastPost";
    public static final String COMMUNITY_LAST_REPLY = "lastReply";
    public static final String COMMUNITY_RECOMMEND = "recommend";


    // 社区类型
    public static final int COMMUNITY_TYPE_IMAGE = 1; // 图片类型
    public static final int COMMUNITY_TYPE_VIDEO = 2; // 视频类型

    //视频播放百分比
    public static final int PLAY_PERCENT = 33;
    //标签状态
    public static final String DELETED = "DELETED";

    //社区页面类型
    public static final String COMMUNITY_FOUCS = "myFollower";
    public static final String COMMUNITY_DISCOVER = "recommend";


    // 5.5.0 处理资讯详情、专题推荐详情的几种类型
    // 通用类型
    public static final String ALL_TITLE_PREFIX = "h"; // 包含h1\h2\h3
    public static final String LIBRARY_BEER_PREFIX = "beer";
    public static final String MALL_PRODUCT_PREFIX = "mallProduct";
    public static final String NORMAL_IMAGE_PREFIX = "image";
    public static final String ROUNDED_IMAGE_PREFIX = "image";
    public static final String NORMAL_VIDEO_PREFIX = "video";
    public static final String MULTIPLE_TEXT_PREFIX = "text";
    // 对应类型ViewType整数值
    public static int GRAPHIC_H1_TYPE = 1001;
    public static int GRAPHIC_H2_TYPE = 1002;
    public static int GRAPHIC_H3_TYPE = 1003;
    public static int GRAPHIC_TEXT_TYPE = 1004;
    public static int GRAPHIC_IMAGE_TYPE = 1105;
    public static int GRAPHIC_VIDEO_TYPE = 1106;
    public static int GRAPHIC_BEER_TYPE = 1107;
    public static int GRAPHIC_PRODUCT_TYPE = 1108;

}
