using System.Collections;
using System.Collections.Generic;
using System;
using System.Linq;
using UnityEngine;
using UnityEngine.SceneManagement;

using Mirror;

// https://github.com/DapperDino/Mirror-Multiplayer-Tutorials/blob/628b062b138fb48967c03ce6a146c52de979b74e/Assets/Tutorials/Lobby/Scripts/NetworkManagerLobby.cs

public class NetworkManagerWakaMole : NetworkManager
{
    [Scene] [SerializeField] private string menuScene = string.Empty;

    [Header("UI Panel")]
    [SerializeField] private GameObject textWaitingPlayer = null;
    [SerializeField] private GameObject startGameButton = null;

    public static event Action OnClientConnected;
    public static event Action OnClientDisconnected;

    public List<GameObject> GamePlayers { get; } = new List<GameObject>();


    public override void OnStartServer() => spawnPrefabs = Resources.LoadAll<GameObject>("SpawnablePrefabs").ToList();

    public override void OnStartClient()
    {
        var spawnablePrefabs = Resources.LoadAll<GameObject>("SpawnablePrefabs");

        foreach (var prefab in spawnablePrefabs)
        {
            ClientScene.RegisterPrefab(prefab);
        }
    }

    public override void OnClientConnect(NetworkConnection conn)
    {
        base.OnClientConnect(conn);

        OnClientConnected?.Invoke();
    }

    public override void OnClientDisconnect(NetworkConnection conn)
    {
        base.OnClientDisconnect(conn);

        OnClientDisconnected?.Invoke();
    }

    public override void OnServerConnect(NetworkConnection conn)
    {
        if (numPlayers >= maxConnections)
        {
            conn.Disconnect();
            return;
        }

        if (SceneManager.GetActiveScene().path != menuScene)
        {
            conn.Disconnect();
            return;
        }
    }

    public override void OnServerAddPlayer(NetworkConnection conn)
    {
        if (SceneManager.GetActiveScene().path == menuScene)
        {
            // Instantiate player
            GameObject player = Instantiate(playerPrefab);
            NetworkServer.AddPlayerForConnection(conn, player);


            // Check if the second player has connected
            if (NetworkServer.connections.Count > 1)
            {
                // Show START button
                textWaitingPlayer.SetActive(false);
                startGameButton.SetActive(true);
            } else
            {
                player.GetComponent<PlayerScript>().isHost = true;
            }
        }
    }

    public void StartGame()
    {
        // Load WakaMole scene
        ServerChangeScene("WakaMoleAR");
    }
}
