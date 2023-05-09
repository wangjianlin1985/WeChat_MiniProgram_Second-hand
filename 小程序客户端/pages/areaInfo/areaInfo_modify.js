var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    areaId: 0, //记录编号
    areaName: "", //区域名称
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  //提交服务器修改区域信息信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/areaInfo/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var areaInfolist_page = pages[pages.length - 2];
      var areaInfos = areaInfolist_page.data.areaInfos
      for(var i=0;i<areaInfos.length;i++) {
        if(areaInfos[i].areaId == res.data.areaId) {
          areaInfos[i] = res.data
          break
        }
      }
      areaInfolist_page.setData({areaInfos:areaInfos})
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
    var areaId = params.areaId
    var url = config.basePath + "api/areaInfo/get/" + areaId
    utils.sendRequest(url, {}, function (areaInfoRes) {
      wx.stopPullDownRefresh()
      self.setData({
        areaId: areaInfoRes.data.areaId,
        areaName: areaInfoRes.data.areaName,
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

