type Point = {
  x: number;
  y: number;
};
export function lerp(a: Point, b: Point, time: number, speed: number) {
  let dx = b.x - a.x;
  let dy = b.y - a.y;
  let distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	if (distance < 0.0001) {
		return b;
	}
  let totalTime = distance / speed;

  const normalizedDx = (dx * speed * Math.pow(time, 2)) / distance;
  const normalizedDy = (dy * speed * Math.pow(time, 2)) / distance;
  time = Math.min(Math.max(time, 0), totalTime);

  return { x: a.x + normalizedDx, y: a.y + normalizedDy };
}
