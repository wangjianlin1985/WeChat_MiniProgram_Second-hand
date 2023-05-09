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

  //提交服务器修改商品分类信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/productClass/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var productClasslist_page = pages[pages.length - 2];
      var productClasss = productClasslist_page.data.productClasss
      for(var i=0;i<productClasss.length;i++) {
        if(productClasss[i].productClassId == res.data.productClassId) {
          productClasss[i] = res.data
          break
        }
      }
      productClasslist_page.setData({productClasss:productClasss})
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

