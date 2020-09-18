using Mirror;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MoleSpawner : NetworkBehaviour
{
    [SerializeField]
    private GameObject enemyPrefab;

    [SerializeField]
    private float spawnInterval = 1.0f;

    [SerializeField]
    private float enemySpeed = 1.0f;

    void SpawnMole()
    {
        Vector3 spawnPosition = new Vector3(this.transform.position.x + Random.Range(-4.0f, 4.0f), this.transform.position.y + Random.Range(-4.0f, 4.0f), this.transform.position.z + Random.Range(-4.0f, 4.0f));
        GameObject enemy = Instantiate(enemyPrefab, spawnPosition, Quaternion.identity) as GameObject;
        NetworkServer.Spawn(enemy, connectionToClient);
        Destroy(enemy, 8);
    }

    private void OnEnable()
    {
        InvokeRepeating("SpawnMole", this.spawnInterval, this.spawnInterval);
    }

    private void OnDisable()
    {
        CancelInvoke();
    }
}
