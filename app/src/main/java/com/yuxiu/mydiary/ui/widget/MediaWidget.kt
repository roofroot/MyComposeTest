import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.view.Surface
import android.view.TextureView
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.viewinterop.AndroidView
import com.yuxiu.mydiary.MainActivity
import com.yuxiu.mydiary.ui.widget.*
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import tv.danmaku.ijk.media.player.TextureMediaPlayer
import kotlin.math.roundToInt

@Composable
 fun getMedia(entity:MyPageMediaEntity){
    var offsetXFrame = remember {
        mutableStateOf(entity.offsetXFrame)
    }
    var offsetYFrame = remember {
        mutableStateOf(entity.offsetYFrame)
    }
    Box(
        Modifier
            .wrapContentHeight()
            .wrapContentHeight()
            .offset {
                IntOffset(
                    entity.offsetXFrame.value.roundToInt(),
                   entity.offsetYFrame.value.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures({
                }, {
                }, {
                }, {
                        change, dragAmount ->
                    change.consumeAllChanges()
                    entity.offsetXFrame.value += dragAmount.x
                    entity.offsetX.value += dragAmount.x
//                        }
//                        if(offsetYFrame.value+dragAmount.y>0){
                    entity.offsetYFrame.value += dragAmount.y
                    entity.offsetY.value += dragAmount.y
                })

            },){
        var textureView: TextureView?;
        AndroidView({
                context ->
            textureView= TextureView(context);
            textureView!!
        }
            , Modifier.width(Dp((entity.offsetX.value - entity.offsetXFrame.value) / MainActivity.density))
                .height(Dp((entity.offsetY.value - entity.offsetYFrame.value) / MainActivity.density))
        ){
            it.surfaceTextureListener=object: TextureView.SurfaceTextureListener{
                override fun onSurfaceTextureAvailable(
                    surface: SurfaceTexture,
                    width: Int,
                    height: Int
                ) {
                    mSurface = Surface(surface)
                    if (entity.player == null) {
                        entity.player = IjkMediaPlayer();
                        entity.player?.setAudioStreamType(
                            AudioManager
                                .STREAM_MUSIC
                        );
                        entity.player?.setDataSource(entity.mediaPath);
                        entity.player?.prepareAsync();
                    }
                    textureMediaPlayer = TextureMediaPlayer(entity.player)
                    textureMediaPlayer?.setSurface(mSurface)

                }
                override fun onSurfaceTextureSizeChanged(
                    surface: SurfaceTexture,
                    width: Int,
                    height: Int
                ) {
                }
                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                    return true
                }
                override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                }
            }
        }

    }
    ActionWeight(entity = entity)
}