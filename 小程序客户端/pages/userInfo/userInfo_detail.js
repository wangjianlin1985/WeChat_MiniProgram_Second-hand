var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    user_name: "", //手机用户名
    password: "", //登陆密码
    realname: "", //姓名
    sex: "", //性别
    bornDate: "", //出生日期
    qq: "", //用户qq
    address: "", //家庭地址
    email: "", //用户邮箱
    myPhotoUrl: "", //用户头像
    memo: "", //附加信息
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var user_name = params.user_name
    var url = config.basePath + "api/userInfo/get/" + user_name
    utils.sendRequest(url, {}, function (userInfoRes) {
      wx.stopPullDownRefresh()
      self.setData({
        user_name: userInfoRes.data.user_name,
        password: userInfoRes.data.password,
        realname: userInfoRes.data.realname,
        sex: userInfoRes.data.sex,
        bornDate: userInfoRes.data.bornDate,
        qq: userInfoRes.data.qq,
        address: userInfoRes.data.address,
        email: userInfoRes.data.email,
        myPhoto: userInfoRes.data.myPhoto,
        myPhotoUrl: userInfoRes.data.myPhotoUrl,
        memo: userInfoRes.data.memo,
      })
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  }

})

