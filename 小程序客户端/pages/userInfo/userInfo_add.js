var utils = require("../../utils/common.js");
var config = require("../../utils/config.js");

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
    myPhotoList: [],
    memo: "", //附加信息
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  //选择出生日期
  bind_bornDate_change: function (e) {
    this.setData({
      bornDate: e.detail.value
    })
  },
  //清空出生日期
  clear_bornDate: function (e) {
    this.setData({
      bornDate: "",
    });
  },

  /*提交添加用户信息到服务器 */
  formSubmit: function (e) {
    var self = this;
    var formData = e.detail.value;
    var url = config.basePath + "api/userInfo/add";
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '发布成功',
        icon: 'success',
        duration: 500
      })

      //提交成功后重置表单数据
      self.setData({
        user_name: "",
    password: "",
    realname: "",
    sex: "",
    bornDate: "",
    qq: "",
    address: "",
    email: "",
        myPhoto: "upload/NoImage.jpg",
        myPhotoList: [],
    memo: "",
        loadingHide: true,
        loadingText: "网络操作中。。",
      })
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
          loadingHide: false
        });
        utils.sendUploadImage(config.basePath + "upload/image", tempFilePaths[0], function (res) {
          wx.stopPullDownRefresh()
          setTimeout(function () {
            self.setData({
              myPhoto: res.data,
              loadingHide: true
            });
          }, 200);
        });
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var token = wx.getStorageSync('authToken');
    if (!token) {
      wx.navigateTo({
        url: '../mobile/mobile',
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  }
})

