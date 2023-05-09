var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

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

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var announceId = params.announceId
    var url = config.basePath + "api/announce/get/" + announceId
    utils.sendRequest(url, {}, function (announceRes) {
      wx.stopPullDownRefresh()
      self.setData({
        announceId: announceRes.data.announceId,
        announceTitle: announceRes.data.announceTitle,
        announceContent: announceRes.data.announceContent,
        announceDate: announceRes.data.announceDate,
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

