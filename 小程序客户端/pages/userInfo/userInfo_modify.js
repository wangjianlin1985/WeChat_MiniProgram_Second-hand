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
    myPhoto: "upload/NoImage.jpg", //用户头像
    myPhotoUrl: "",
    myPhotoList: [],
    memo: "", //附加信息
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  //选择出生日期事件
  bind_bornDate_change: function (e) {
    this.setData({
      bornDate: e.detail.value
    })
  },
  //清空出生日期事件
  clear_bornDate: function (e) {
    this.setData({
      bornDate: "",
    });
  },

  //选择用户头像上传
  select_myPhoto: function (e) {
    var self = this
    wx.chooseImage({
      count: 1,   //可以上传一张图片
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        var tempFilePaths = res.tempFilePaths
        self.setData({
          myPhotoList: tempFilePaths,
          loadingHide: false, 
        });

        utils.sendUploadImage(config.basePath + "upload/image", tempFilePaths[0], function (res) {
          wx.stopPullDownRefresh()
          setTimeout(function () {
            self.setData({
              myPhoto: res.data,
              loadingHide: true
            })
          }, 200)
        })
      }
    })
  },

  //提交服务器修改用户信息信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/userInfo/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var userInfolist_page = pages[pages.length - 2];
      var userInfos = userInfolist_page.data.userInfos
      for(var i=0;i<userInfos.length;i++) {
        if(userInfos[i].user_name == res.data.user_name) {
          userInfos[i] = res.data
          break
        }
      }
      userInfolist_page.setData({userInfos:userInfos})
      setTimeout(function () {
        wx.navigateBack({})
      }, 500) 
    })
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
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  },

})

