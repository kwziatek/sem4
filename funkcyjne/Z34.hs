type Foldable :: (* -> *) -> Constraint
class Foldable t where
  ...
  sum :: Num a => t a -> a
  ...
  	-- Defined in ‘Data.Foldable’


type Foldable :: (* -> *) -> Constraint
class Foldable t where
  ...
  product :: Num a => t a -> a
  	-- Defined in ‘Data.Foldable’

all :: Foldable t => (a -> Bool) -> t a -> Bool
  	-- Defined in ‘Data.Foldable’

any :: Foldable t => (a -> Bool) -> t a -> Bool
  	-- Defined in ‘Data.Foldable’

