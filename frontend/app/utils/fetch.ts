
export async function queryData(params: string) {
  const response = await fetch(`http://localhost:8080${params}`, {
    method: "GET",
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    },
  });
  return response.json();
}

export async function postData(params: string) {
  const response = await fetch(`http://localhost:8080${params}`, {
    method: "POST",
    headers: {
      //"Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    },
  });
}
