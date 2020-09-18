using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using Mirror;

public class NetworkGamePlayerLobby : NetworkBehaviour
{
    private NetworkManagerWakaMoleLobby room;
    private NetworkManagerWakaMoleLobby Room
    {
        get
        {
            if (room != null) { return room; }
            return room = NetworkManager.singleton as NetworkManagerWakaMoleLobby;
        }
    }

    //public override void OnStartClient()
    //{
    //    DontDestroyOnLoad(gameObject);
    //    Room.GamePlayers.Add(this);
    //}

    //public override void OnNetworkDestroy()
    //{
    //    Room.GamePlayers.Remove(this);
    //}

    
}
