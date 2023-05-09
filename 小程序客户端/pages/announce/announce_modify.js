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

  //选择发布日期事件
  bind_announceDate_change: function (e) {
    this.setData({
      announceDate: e.detail.value
    })
  },
  //清空发布日期事件
  clear_announceDate: function (e) {
    this.setData({
      announceDate: "",
    });
  },

  //提交服务器修改公告信息信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/announce/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var announcelist_page = pages[pages.length - 2];
      var announces = announcelist_page.data.announces
      for(var i=0;i<announces.length;i++) {
        if(announces[i].announceId == res.data.announceId) {
          announces[i] = res.data
          break
        }
      }
      announcelist_page.setData({announces:announces})
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

