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

  //提交服务器修改价格区间信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/priceRegion/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var priceRegionlist_page = pages[pages.length - 2];
      var priceRegions = priceRegionlist_page.data.priceRegions
      for(var i=0;i<priceRegions.length;i++) {
        if(priceRegions[i].regionId == res.data.regionId) {
          priceRegions[i] = res.data
          break
        }
      }
      priceRegionlist_page.setData({priceRegions:priceRegions})
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

