using System.Collections;
using System.Collections.Generic;
using UnityEngine;

using Mirror;

using System;
using System.Linq;
using UnityEngine.SceneManagement;

public class NetworkManagerLobby : NetworkManager
{
    public override void Start()
    {
        base.Start();

        base.networkAddress = "localhost";
    }

    public void NewGameSelect()
    {
        // Hide buttons and show IP

        base.StartHost();
    }

    public void JoinGameSelect()
    {
        // Hide buttons and show "Joining game" info

        base.StartClient();
    }

    public void UpdateNetworkAddress(string newNetworkAddress)
    {
        networkAddress = newNetworkAddress;
    }
}
