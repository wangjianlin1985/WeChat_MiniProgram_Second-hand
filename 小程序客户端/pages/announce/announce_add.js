var utils = require("../../utils/common.js");
var config = require("../../utils/config.js");

Page({
  /**
   * 页面的初始数据
   */
  data: {
    announceId: 0, //公告编号
    announceTitle: "", //公告标题
    announceContent: "", //公告内容
    announceDate: "", //发布日期
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  //选择发布日期
  bind_announceDate_change: function (e) {
    this.setData({
      announceDate: e.detail.value
    })
  },
  //清空发布日期
  clear_announceDate: function (e) {
    this.setData({
      announceDate: "",
    });
  },

  /*提交添加公告信息到服务器 */
  formSubmit: function (e) {
    var self = this;
    var formData = e.detail.value;
    var url = config.basePath + "api/announce/add";
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '发布成功',
        icon: 'success',
        duration: 500
      })

      //提交成功后重置表单数据
      self.setData({
        announceId: 0,
    announceTitle: "",
    announceContent: "",
    announceDate: "",
        loadingHide: true,
        loadingText: "网络操作中。。",
      })
    });
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

