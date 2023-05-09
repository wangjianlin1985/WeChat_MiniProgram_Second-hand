//index.js
//获取应用实例
const app = getApp()
var config = require("../../utils/config.js");
var utils = require("../../utils/common.js");

Page({
  data: { 
    // 首页轮播图
    imgUrls: [
      '/images/a.png',
      '/images/b.png',
      '/images/c.png'
    ], 
    indicatorDots: false,
    autoplay: true,
    interval: 3000,
    duration: 800,

    //motto: 'Hello World',
    //userInfo: {},
    //hasUserInfo: false,
    //canIUse: wx.canIUse('button.open-type.getUserInfo')
    productInfos: [], //界面显示的商品信息列表数据
    loading_hide: true, //是否隐藏加载动画
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  // 加载最新5个商品信息列表
  listNewProductInfo: function () {
    var self = this
    var url = config.basePath + "api/productInfo/newlist"
     
    self.setData({
      loading_hide: false,  //显示加载动画
    })
    //提交查询参数到服务器查询数据列表
    utils.sendRequest(url, {}, function (res) {
      wx.stopPullDownRefresh()
      setTimeout(function () {
        self.setData({
          productInfos: self.data.productInfos.concat(res.data.list), 
          loading_hide: true
        })
      }, 500)
   
    })
  },

  onLoad: function () {
    this.listNewProductInfo();

    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }


  
})
