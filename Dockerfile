FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src
COPY BrasilBurger.Web/BrasilBurger.Web.csproj ./BrasilBurger.Web/
RUN dotnet restore ./BrasilBurger.Web/BrasilBurger.Web.csproj
COPY BrasilBurger.Web/ ./BrasilBurger.Web/
WORKDIR /src/BrasilBurger.Web
RUN dotnet publish -c Release -o /app/publish

FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS final
WORKDIR /app
COPY --from=build /app/publish .
EXPOSE 10000
ENTRYPOINT ["dotnet", "BrasilBurger.Web.dll", "--urls", "http://0.0.0.0:10000"]
