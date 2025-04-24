with Ada.Text_IO;
procedure Hello is
   A, B, C : Integer;
begin
   A := Integer'Value (Ada.Text_IO.Get_Line);
   B := Integer'Value (Ada.Text_IO.Get_Line);
   C := A + B;

   if C = 0 then
      Ada.Text_IO.Put_Line ("Zero");
   elsif C > 0 then
      Ada.Text_IO.Put_Line ("Positive:" & Integer'Image (C));
   else
      Ada.Text_IO.Put_Line ("Negative:" & Integer'Image (C));
   end if;
end Hello;