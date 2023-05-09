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

  //提交服务器修改新旧程度信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/newOldLevel/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var newOldLevellist_page = pages[pages.length - 2];
      var newOldLevels = newOldLevellist_page.data.newOldLevels
      for(var i=0;i<newOldLevels.length;i++) {
        if(newOldLevels[i].levelId == res.data.levelId) {
          newOldLevels[i] = res.data
          break
        }
      }
      newOldLevellist_page.setData({newOldLevels:newOldLevels})
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

