var utils = require("../../utils/common.js");
var config = require("../../utils/config.js");

Page({
  /**
   * 页面的初始数据
   */
  data: {
    queryViewHidden: true, //是否隐藏查询条件界面
    announceTitle: "", //公告标题查询关键字
    announceDate: "", //发布日期查询关键字
    announces: [], //界面显示的公告信息列表数据
    page_size: 8, //每页显示几条数据
    page: 1,  //当前要显示第几页
    totalPage: null, //总的页码数
    loading_hide: true, //是否隐藏加载动画
    nodata_hide: true, //是否隐藏没有数据记录提示
  },

  // 加载公告信息列表
  listAnnounce: function () {
    var self = this
    var url = config.basePath + "api/announce/list"
    //如果要显示的页码超过总页码不操作
    if(self.data.totalPage != null && self.data.page > self.data.totalPage) return
    self.setData({
      loading_hide: false,  //显示加载动画
    })
    //提交查询参数到服务器查询数据列表
    utils.sendRequest(url, {
      "announceTitle": self.data.announceTitle,
      "announceDate": self.data.announceDate,
      "page": self.data.page,
      "rows": self.data.page_size,
    }, function (res) { 
      wx.stopPullDownRefresh()
      setTimeout(function () {  
        self.setData({
          announces: self.data.announces.concat(res.data.list),
          totalPage: res.data.totalPage,
          loading_hide: true
        })
      }, 500)
      //如果当前显示的是最后一页，则显示没数据提示
      if(self.data.page == self.data.totalPage) {
        self.setData({
          nodata_hide: false,
        })
      }
    })
  },

  //显示或隐藏查询视图切换
  toggleQueryViewHide: function () {
    this.setData({
      queryViewHidden: !this.data.queryViewHidden,
    })
  },

  //点击查询按钮的事件
  queryAnnounce: function(e) {
    var self = this
    self.setData({
      page: 1,
      totalPage: null,
      announces: [],
      loading_hide: true, //隐藏加载动画
      nodata_hide: true, //隐藏没有数据记录提示 
      queryViewHidden: true, //隐藏查询视图
    })
    self.listAnnounce()
  },

  //查询参数发布日期选择事件
  bind_announceDate_change: function (e) {
    this.setData({
      announceDate: e.detail.value
    })
  },
  //清空查询参数发布日期
  clear_announceDate: function (e) {
    this.setData({
      announceDate: "",
    })
  },

  //绑定查询参数输入事件
  searchValueInput: function (e) {
    var id = e.target.dataset.id
    if (id == "announceTitle") {
      this.setData({
        "announceTitle": e.detail.value,
      })
    }

  },

  init_query_params: function() { 
    var self = this
    var url = null
  },

  //删除公告信息记录
  removeAnnounce: function (e) {
    var self = this
    var announceId = e.currentTarget.dataset.announceid
    wx.showModal({
      title: '提示',
      content: '确定要删除announceId=' + announceId + '的记录吗？',
      success: function (sm) {
        if (sm.confirm) {
          // 用户点击了确定 可以调用删除方法了
          var url = config.basePath + "api/announce/delete/" + announceId
          utils.sendRequest(url, null, function (res) {
            wx.stopPullDownRefresh();
            wx.showToast({
              title: '删除成功',
              icon: 'success',
              duration: 500
            })
            //删除公告信息后客户端同步删除数据
            var announces = self.data.announces;
            for (var i = 0; i < announces.length; i++) {
              if (announces[i].announceId == announceId) {
                announces.splice(i, 1)
                break
              }
            }
            self.setData({ announces: announces })
          })
        } else if (sm.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.listAnnounce()
    this.init_query_params()
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var self = this
    self.setData({
      page: 1,  //显示最新的第1页结果
      announces: [], //清空公告信息数据
      nodata_hide: true, //隐藏没数据提示
    })
    self.listAnnounce()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var self = this
    if (self.data.page < self.data.totalPage) {
      self.setData({
        page: self.data.page + 1, 
      })
      self.listAnnounce()
    }
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }

})

