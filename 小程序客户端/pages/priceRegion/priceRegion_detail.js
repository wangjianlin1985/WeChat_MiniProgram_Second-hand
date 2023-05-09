var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    regionId: 0, //记录编号
    regionName: "", //价格区间
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var regionId = params.regionId
    var url = config.basePath + "api/priceRegion/get/" + regionId
    utils.sendRequest(url, {}, function (priceRegionRes) {
      wx.stopPullDownRefresh()
      self.setData({
        regionId: priceRegionRes.data.regionId,
        regionName: priceRegionRes.data.regionName,
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

