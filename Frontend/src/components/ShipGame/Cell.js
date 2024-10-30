function Cell({ isHit, isShip, onClick }) {
    const cellClass = `ship_cell ${isShip ? "ship" : ""} ${isHit ? "hit" : ""} ${isShip && isHit ? "ship-hit" : ""}`;
    return <div className={cellClass} onClick={onClick} />;
}

export default Cell;
