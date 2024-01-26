# EcoPetsBoost-ISetsAddon
EcoPets plugin is required

This addon allows you to boost the amount of experience gained when players are leveling their pets.

Using the below settings, the player's pet will receive a 200% boost on the amount of experience it gains. For instance, if it gained 10, they will receive 30.

```yaml
Boosts:
  - Namespace: ECO_PETS
    Type: Pets
    Percent: true
    Settings:
      Boost_Amount: 200
```
