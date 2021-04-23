package com.yuxiu.mydiary.ui.widget

import tv.danmaku.ijk.media.player.IjkMediaPlayer

class test {
    fun initIjk(player: IjkMediaPlayer?): IjkMediaPlayer {
        var player = player
        player = IjkMediaPlayer()
        return player
    }
}