# Mikroserwisowa Aplikacja Gier

## Opis projektu
Projekt to aplikacja mikroserwisowa umożliwiająca grę w trzy różne gry: Pong, Kółko i Krzyżyk oraz Statki. System wykorzystuje technologie takie jak Spring Boot, Quarkus, PostgreSQL oraz React. Każdy mikroserwis pełni określoną funkcję i komunikuje się poprzez API.

## Technologie
- **Spring Boot** - Gateway, Security, Tablica wyników, Gra w Ponga
- **Quarkus** - Gra w Kółko i Krzyżyk, Gra w Statki
- **PostgreSQL** - Baza danych dla gier i wyników
- **React** - Frontend aplikacji
- **JWT Token** - Autoryzacja i uwierzytelnianie użytkowników

## Architektura systemu
1. **Frontend (React)**
   - Komunikuje się z gatewayem w Spring Boot za pomocą JWT tokenów
   - Obsługuje interfejs użytkownika dla wszystkich gier i tablicy wyników
   
2. **Gateway (Spring Boot)**
   - Odpowiada za uwierzytelnianie użytkowników
   - Obsługuje żądania z frontendu i przekazuje je do odpowiednich mikroserwisów

3. **Gra w Ponga (Spring Boot)**
   - Logika gry realizowana na froncie w React
   - Spring Boot odpowiada za obsługę wyników i komunikację z tablicą wyników

4. **Gra w Kółko i Krzyżyk (Quarkus)**
   - Obsługuje rozgrywkę
   - Komunikuje się z bazą danych PostgreSQL

5. **Gra w Statki (Quarkus)**
   - Obsługuje rozgrywkę
   - Komunikuje się z bazą danych PostgreSQL

6. **Tablica wyników (Spring Boot)**
   - Przechowuje i obsługuje wyniki graczy
   - Komunikuje się z bazą danych PostgreSQL

