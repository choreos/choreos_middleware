#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-service655ab0b4-7476-46db-b38c-fdffa476cc5d" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service655ab0b4-7476-46db-b38c-fdffa476cc5d::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/655ab0b4-7476-46db-b38c-fdffa476cc5d.war", :immediately
end

ruby_block "remove-service655ab0b4-7476-46db-b38c-fdffa476cc5d" do
  block do
  	node.run_list.remove("recipe[service655ab0b4-7476-46db-b38c-fdffa476cc5d::jar]")
  end
  only_if { node.run_list.include?("recipe[service655ab0b4-7476-46db-b38c-fdffa476cc5d::jar]") }
end

ruby_block "remove-deactivate-service655ab0b4-7476-46db-b38c-fdffa476cc5d" do
  block do
    node.run_list.remove("recipe[deactivate-service655ab0b4-7476-46db-b38c-fdffa476cc5d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service655ab0b4-7476-46db-b38c-fdffa476cc5d]") }
end
