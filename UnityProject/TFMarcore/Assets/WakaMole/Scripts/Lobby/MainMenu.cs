using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using UnityEngine;
using TMPro;
using Mirror;

// https://github.com/DapperDino/Mirror-Multiplayer-Tutorials/blob/master/Assets/Tutorials/Lobby/Scripts/MainMenu.cs

public class MainMenu : MonoBehaviour
{
    [SerializeField] private NetworkManagerWakaMole networkManager = null;

    [Header("UI")]
    [SerializeField] private GameObject landingPagePanel = null;
    [SerializeField] private GameObject waitingPagePanel = null;
    [SerializeField] private TMP_Text IP_text = null;

    public void Start()
    {
        networkManager = NetworkManagerWakaMole.singleton as NetworkManagerWakaMole;
    }

    public void HostLobby()
    {
        networkManager.StartHost();

        // Show device IP
        IP_text.text = LocalIPAddress().ToString();

        landingPagePanel.SetActive(false);
        waitingPagePanel.SetActive(true);
    }

    public void StartGame()
    {
        networkManager.StartGame();
    }

    private IPAddress LocalIPAddress()
    {
        if (!System.Net.NetworkInformation.NetworkInterface.GetIsNetworkAvailable())
        {
            return null;
        }

        IPHostEntry host = Dns.GetHostEntry(Dns.GetHostName());

        return host
            .AddressList
            .LastOrDefault(ip => ip.AddressFamily == AddressFamily.InterNetwork);
    }
}
