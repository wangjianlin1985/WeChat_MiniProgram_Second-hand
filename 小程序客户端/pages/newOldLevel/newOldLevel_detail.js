var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    levelId: 0, //记录编号
    levelName: "", //新旧程度
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var levelId = params.levelId
    var url = config.basePath + "api/newOldLevel/get/" + levelId
    utils.sendRequest(url, {}, function (newOldLevelRes) {
      wx.stopPullDownRefresh()
      self.setData({
        levelId: newOldLevelRes.data.levelId,
        levelName: newOldLevelRes.data.levelName,
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

