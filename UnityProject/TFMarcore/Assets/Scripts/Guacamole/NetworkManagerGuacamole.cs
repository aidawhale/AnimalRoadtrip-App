using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Mirror;

public class NetworkManagerGuacamole : NetworkManager
{
    GameObject map;

    public override void OnServerAddPlayer(NetworkConnection conn)
    {

    }

    public override void OnServerDisconnect(NetworkConnection conn)
    {
        // TODO ...

        base.OnServerDisconnect(conn);
    }
}
