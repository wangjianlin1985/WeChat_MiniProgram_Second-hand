var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    productClassId: 0, //类别编号
    productClassName: "", //类别名称
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var productClassId = params.productClassId
    var url = config.basePath + "api/productClass/get/" + productClassId
    utils.sendRequest(url, {}, function (productClassRes) {
      wx.stopPullDownRefresh()
      self.setData({
        productClassId: productClassRes.data.productClassId,
        productClassName: productClassRes.data.productClassName,
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

