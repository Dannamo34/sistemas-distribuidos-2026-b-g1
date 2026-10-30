
param(
    [Parameter(Mandatory = $true)]
    [string]$Url
)

Write-Host "Testing unauthorized access against: $Url"

if (-not ($Url -match '^https?://')) {
    throw "The endpoint must be a valid HTTP or HTTPS URL."
}

try {
    $response = Invoke-WebRequest `
        -Uri $Url `
        -Method Get `
        -UseBasicParsing `
        -TimeoutSec 30

    $statusCode = [int]$response.StatusCode
}
catch {
    if ($_.Exception.Response) {
        $statusCode = [int]$_.Exception.Response.StatusCode
    }
    else {
        throw "The endpoint could not be reached: $($_.Exception.Message)"
    }
}

Write-Host "HTTP status received: $statusCode"

if ($statusCode -eq 401 -or $statusCode -eq 403) {
    Write-Host "PASS: Unauthorized access was rejected."
    exit 0
}

if ($statusCode -ge 200 -and $statusCode -lt 300) {
    Write-Error "FAIL: The endpoint accepted an unauthenticated request."
    exit 1
}

Write-Error "INCONCLUSIVE: Expected HTTP 401 or 403, received $statusCode."
exit 1
