package com.example.masterhelper.mediaworker;

public class SoundPoolQueue {
}

/**
 * class SoundPoolQueue(private val context: Context, maxStreams: Int) {
 *     companion object {
 *         private const val LOG_TAG = "SoundPoolQueue"
 *         private const val SOUND_POOL_HANDLER_THREAD_NAME = "SoundPoolQueueThread"
 *         private const val ACTION_PLAY_SOUND = 1
 *
 *         @JvmStatic
 *         fun getSoundDuration(context: Context, soundResId: Int) : Long {
 *             val assetsFileDescriptor = context.resources.openRawResourceFd(soundResId)
 *             val mediaMetadataRetriever = MediaMetadataRetriever()
 *
 *             mediaMetadataRetriever.setDataSource(
 *                     assetsFileDescriptor.fileDescriptor,
 *                     assetsFileDescriptor.startOffset,
 *                     assetsFileDescriptor.length)
 *
 *             val durationString = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
 *             val duration = durationString.toLong()
 *
 *             logDebug("SoundPoolQueue::getSoundDuration(): Sound duration millis: $durationString")
 *
 *             assetsFileDescriptor.close()
 *             return duration
 *         }
 *
 *         @JvmStatic
 *         private fun logDebug(message: String) {
 *             if(!BuildConfig.DEBUG) {
 *                 return
 *             }
 *
 *             Log.d(LOG_TAG, message)
 *         }
 *     }
 *
 *     private var soundPool = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
 *         val attrs = AudioAttributes.Builder()
 *                 .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
 *                 .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
 *                 .build()
 *
 *         SoundPool.Builder()
 *                 .setMaxStreams(maxStreams)
 *                 .setAudioAttributes(attrs)
 *                 .build()
 *     }
 *     else {
 *         @Suppress("DEPRECATION")
 *         SoundPool(maxStreams, AudioManager.STREAM_NOTIFICATION, 0)
 *     }
 *
 *     var soundPoolQueueListener : SoundPoolQueueListener? = null
 *
 *     private val soundPoolHandlerThread = SoundPoolQueueThread().apply { start() }
 *     private val soundPoolSoundsSparseArray = SparseArray<SoundPoolSound>()
 *     private val soundPoolSoundsQueue = LinkedList<SoundPoolSound>()
 *
 *     fun addSound(soundResId: Int, leftVolume: Float, rightVolume: Float, priority: Int, loop: Boolean, rate: Float) {
 *         val durationMillis = getSoundDuration(context = context, soundResId = soundResId)
 *         val soundId = soundPool.load(context, soundResId, priority)
 *
 *         soundPoolSoundsSparseArray.put(soundResId,
 *                 SoundPoolSound(durationMillis, soundResId, soundId, leftVolume, rightVolume, priority, loop, rate))
 *     }
 *
 *     fun playSound(soundResId: Int) {
 *         logDebug("SoundPoolQueue::playSound()")
 *         soundPoolSoundsQueue.add(soundPoolSoundsSparseArray[soundResId])
 *         soundPoolHandlerThread.handler?.sendEmptyMessage(ACTION_PLAY_SOUND)
 *     }
 *
 *     inner class SoundPoolQueueThread : HandlerThread(SOUND_POOL_HANDLER_THREAD_NAME) {
 *         var handler: Handler? = null
 *
 *         override fun onLooperPrepared() {
 *             super.onLooperPrepared()
 *
 *             handler = object : Handler(looper) {
 *                 override fun handleMessage(msg: Message) {
 *                     super.handleMessage(msg)
 *
 *                     if(msg.what == ACTION_PLAY_SOUND && handler!!.hasMessages(ACTION_PLAY_SOUND)) {
 *                         return
 *                     }
 *
 *                     if(soundPoolSoundsQueue.isEmpty()) {
 *                         logDebug("SoundPoolHandlerThread: queue is empty.")
 *                         handler!!.removeMessages(ACTION_PLAY_SOUND)
 *                         return
 *                     }
 *
 *                     logDebug("SoundPoolHandlerThread: Playing sound!")
 *                     logDebug("SoundPoolHandlerThread: ${soundPoolSoundsQueue.size} sounds left for playing.")
 *
 *                     val soundPoolSound = soundPoolSoundsQueue.pop()
 *                     soundPool.play(soundPoolSound.soundPoolSoundId,
 *                             soundPoolSound.leftVolume,
 *                             soundPoolSound.rightVolume,
 *                             soundPoolSound.priority,
 *                             if(soundPoolSound.loop) { 1 } else { 0 },
 *                             soundPoolSound.rate)
 *
 *                     try {
 *                         Thread.sleep(soundPoolSound.duration)
 *                     }
 *                     catch (ex: InterruptedException) { }
 *
 *                     //soundPoolQueueListener?.onSoundPlaybackCompleted(soundPoolSound.soundResId)
 *                     sendEmptyMessage(0)
 *                 }
 *             }
 *         }
 *     }
 *
 *     interface SoundPoolQueueListener {
 *         fun onSoundPlaybackCompleted(soundResId: Int)
 *     }
 * }
 * */