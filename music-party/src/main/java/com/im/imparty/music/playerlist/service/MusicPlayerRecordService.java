package com.im.imparty.music.playerlist.service;

import com.im.imparty.common.vo.PlaySongInfo;
import com.im.imparty.music.playerlist.entity.MusicPlayerRecordDomain;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 2
 * @since 2023-05-07
 */
public interface MusicPlayerRecordService extends IService<MusicPlayerRecordDomain> {

    void addMusic(Integer roomId, String songId, String curUsr);

    List<PlaySongInfo> selectAllByRoomId(Integer roomId);
}
